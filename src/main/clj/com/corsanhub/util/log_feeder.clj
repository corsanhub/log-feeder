(ns com.corsanhub.util.log-feeder
  (:import (java.text SimpleDateFormat)
           (java.util Date))
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.stacktrace :as stk]))

(defn gte [value1 value2]
  (or (> value1 value2) (= value1 value2)))

(defn lte [value1 value2]
  (or (< value1 value2) (= value1 value2)))

(defn parse-int [s]
  (Integer. (re-find #"\d+" s)))

(defn parse-date [value format-str]
  (try
    (let [format (SimpleDateFormat. format-str)
          date   (.parse format value)]
      date)
    (catch Exception e
      ;(stk/print-stack-trace e)
      )))

(defn extract-date [line initial-position final-position date-format]
  (try
    (let [date-str (str/trim (subs line (dec initial-position) final-position))
          date     (parse-date date-str date-format)]
      date)
    (catch Exception e
      ;(stk/print-stack-trace e)
      )))

(defn start-feed [input-file initial-position final-position date-format speed-rate output-file]
  (let [_                  (println "Feeding log ...")
        initial-millis     (.getTime (Date.))
        initial-log-millis (atom nil)
        freader            (io/reader input-file)
        fwriter            (io/writer output-file :append true)]
    (doseq [line (line-seq freader)]
      (let [log-date (extract-date line initial-position final-position date-format)]
        (if log-date (let [current_millis (.getTime (Date.))
                           _              (if (not @initial-log-millis)
                                            (reset! initial-log-millis (.getTime log-date)))
                           log-millis     (.getTime log-date)
                           log-offset     (- log-millis @initial-log-millis)
                           offset         (- current_millis initial-millis)
                           sleep-time     (- (/ log-offset speed-rate) offset)
                           _              (if (> sleep-time 0) (Thread/sleep sleep-time))]
                       (println "line : " line)
                       (do (.write fwriter (str line "\n"))
                           (.flush fwriter))))))))

(defn -main [& args] find
  (println "args count: " (count args))
  (if (not (and args (gte (count args) 4) (lte (count args) 5)))
    (do (println "Usage: log-feeder <input-file> <date-position> <date-format> <speed-rate> [output-file]\n"
                 "Example: log-feeder input.log 1-23 \"yyyy-MM-dd HH:mm:ss.SSS\" 3:1 output.log"))
    (let [vargs       (into [] args)
          input-file  (get vargs 0)
          _           (println "class: " (class vargs))
          [initial-position final-position] (map #(parse-int %) (str/split (get vargs 1) #"-"))
          date-format (get vargs 2)
          [deliver-speed reference-speed] (map #(parse-int %) (str/split (get vargs 3) #":"))
          speed-rate  (/ deliver-speed reference-speed)
          output-file (if (get vargs 4) (get vargs 4) "output.log")
          ]
      (println "input-file : " input-file)
      (println "initial-position : " initial-position)
      (println "final-position : " final-position)
      (println "date-format : " date-format)
      (println "speed-rate : " speed-rate)
      (println "output-file : " output-file)

      (start-feed input-file initial-position final-position date-format speed-rate output-file))))
