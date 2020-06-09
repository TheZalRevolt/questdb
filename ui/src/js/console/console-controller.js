import Clipboard from "clipboard"
import $ from "jquery"

import * as qdb from "./globals"

const divSqlPanel = $(".js-sql-panel")
const divExportUrl = $(".js-export-url")
const win = $(window)
const grid = $("#grid")
const quickVis = $("#quick-vis")
const toggleChartBtn = $("#js-toggle-chart")
const toggleGridBtn = $("#js-toggle-grid")

let topHeight = 350

function resize() {
  $("#editor").css("flex-basis", topHeight)
}

function loadSplitterPosition() {
  if (typeof Storage !== "undefined") {
    const n = localStorage.getItem("splitter.position")
    if (n) {
      topHeight = parseInt(n)
      if (!topHeight) {
        topHeight = 350
      }
    }
  }
}

function saveSplitterPosition() {
  if (typeof Storage !== "undefined") {
    localStorage.setItem("splitter.position", topHeight)
  }
}

function toggleVisibility(x, name) {
  if (name === "console") {
    visible = true
    divSqlPanel.show()
  } else {
    visible = false
    divSqlPanel.hide()
  }
}

function toggleChart() {
  toggleChartBtn.addClass("active")
  toggleGridBtn.removeClass("active")
  grid.css("display", "none")
  quickVis.css("display", "flex")
}

function toggleGrid() {
  toggleChartBtn.removeClass("active")
  toggleGridBtn.addClass("active")
  grid.css("display", "flex")
  quickVis.css("display", "none")
}

export function setupConsoleController(bus) {
  win.bind("resize", resize)
  bus.on(qdb.MSG_QUERY_DATASET, function (e, m) {
    divExportUrl.val(qdb.toExportUrl(m.query))
  })

  divExportUrl.click(function () {
    this.select()
  })

  /* eslint-disable no-new */
  new Clipboard(".js-export-copy-url")
  $(".js-query-refresh").click(function () {
    $(".js-query-refresh .fa").addClass("fa-spin")
    bus.trigger("grid.refresh")
  })

  // named splitter
  bus.on("splitter.console.resize", function (x, e) {
    topHeight += e
    win.trigger("resize")
    bus.trigger("preferences.save")
  })

  bus.on("preferences.save", saveSplitterPosition)
  bus.on("preferences.load", loadSplitterPosition)
  bus.on(qdb.MSG_ACTIVE_PANEL, toggleVisibility)

  grid.grid(bus)
  quickVis.quickVis(bus)

  $("#console-splitter").splitter(bus, "console", 200, 200)

  // wire query publish
  toggleChartBtn.click(toggleChart)
  toggleGridBtn.click(toggleGrid)
  bus.on(qdb.MSG_QUERY_DATASET, toggleGrid)
}
