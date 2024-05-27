# heo

Tools to analyze package-level structure and visualize it

# install

Download the latest jar file from the [release](https://github.com/heowc/heo/releases)

This tool relies on [graphviz](https://graphviz.org/). Please install graphviz first.

# usage

- `d`: Enter the directory path.
- `p`: Enter the package path.
- `o`: Enter the destination file path.

```bash
java -jar heo-0.0.1.jar -d /Users/heowc/Projects/heo -p dev.heowc.heo
```

```text
$ java -jar heo-0.0.1.jar -d /Users/heowc/Projects/heo -p dev.heowc.heo
 __   __  _______  _______
|  | |  ||       ||       |
|  |_|  ||    ___||   _   |
|       ||   |___ |  | |  |
|       ||    ___||  |_|  |
|   _   ||   |___ |       |
|__| |__||_______||_______|

2024-05-01T17:23:52.031+09:00  INFO 72099 --- [           main] c.h.h.c.l.a.ModuleLoaderService          : Loading dev.heowc.heo from ./
2024-05-01T17:23:52.044+09:00  INFO 72099 --- [           main] c.h.h.c.a.a.DependencyAnalysisService    : Analysing project dependencies for modules. size=0
2024-05-01T17:23:52.445+09:00  INFO 72099 --- [           main] c.h.h.c.v.ReportVisualizationService     : Report file created  (file:///Users/heowc/Projects/heo/result-1714551832030.png)
```

## Result

![image sample](./docs/sample.png)

# What's next

- Support native
  - See https://github.com/oracle/graal/issues/8273
- Support plugin (gradle, maven etc)
