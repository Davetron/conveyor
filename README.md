# conveyor
Define and execute pipelines as code

```java
conveyor("Pipeline",
  defaultStage("Build",
    job("Job 1",
      gitClone("https://github.com/Davetron/conveyor.git"),
      maven("clean install")
    )
  )
).start();
```
