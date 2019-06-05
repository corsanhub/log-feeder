(defproject com.corsanhub/log-feeder "0.1.0-SNAPSHOT"
  :description "CorsanHub Utils - Log Feeder"
  :url "http://corsanhub.com/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :plugins      [[lein-libdir "0.1.1"]]

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.apache.logging.log4j/log4j-core "2.8.1"]]

  :main com.corsanhub.util.log-feeder

  :repl-options {:prompt (fn [ns] (str "<" ns "> "))
                 :welcome (println "Welcome to a higher state of consciousness!")
                 :init-ns com.corsanhub.util.log-feeder}

  :source-paths ["src/main/clj"]

  :resource-paths ["src/main/resources"]

  :test-paths ["src/test/clj"]

  :min-lein-version "2.5.3"

  :aot :all
)
