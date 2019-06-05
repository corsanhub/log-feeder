# log-feeder

For running in REPL:
```
(use 'com.corsanhub.util.log-feeder)
(in-ns 'com.corsanhub.util.log-feeder)
(use 'com.corsanhub.util.log-feeder :reload-all)

(-main "data/logs/input.log" "1-23" "yyyy-MM-dd HH:mm:ss,SSS" "3:1" "data/logs/output.log")
```

For seeing output log in command line, use:
```
tail -f data/logs/output.log
```
