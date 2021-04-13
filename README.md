# conveyor
Define and execute pipelines as code

```java
conveyor("Pipeline",
  stage("Build",
    job("Job 1",
      dummyTask("task 1"),
      gitCheckout("https://github.com/Davetron/conveyor.git"),
      maven("clean install")
    )
  )
).start();
```
