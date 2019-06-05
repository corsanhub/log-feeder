(ns com.corsanhub.util.log-feeder-test
  (:use clojure.test)
  (:require [com.corsanhub.util.log-feeder :as probe]))

(with-test
  (defn gte-test []
    (is (probe/gte 3 2))))
