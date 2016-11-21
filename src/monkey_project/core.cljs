(ns monkey-project.core
  (:require [blend4web]
            [monkey-project.engine-interface :as engine]))

(defn spin-the-monkey [time]
    (let [scenes (engine/load-b4w-module :scenes)
          trans  (engine/load-b4w-module :transform)
          monkey (.get-object-by-name scenes "Monkey")]

         (.set-rotation-euler trans monkey 0 2 time)))

(defn ^:export start
  []
  (let [timeline-sensor (.create-timeline-sensor (engine/load-b4w-module :controls))

        sensor-manifold
          (engine/enter-sensor-manifold-data
            false
            "main"
            (.-CT_CONTINUOUS (engine/load-b4w-module :controls))
            (clj->js [timeline-sensor])
            spin-the-monkey)

        load-data
          (engine/enter-b4w-data "monkey_project.json"
           (.getElementById js/document "container"))]

   (engine/init-b4w load-data sensor-manifold)))
