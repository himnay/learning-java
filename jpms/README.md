# <span style="color:hsl(224,80%,58%)">JPMS example (Java Platform Module System)</span>

Three modules, one Maven reactor, no shared classpath:

```
jpms-api      module com.org.jpms.api      exports com.org.jpms.api          (Greeter interface)
jpms-service  module com.org.jpms.service  requires com.org.jpms.api          provides Greeter with EnglishGreeter, SpanishGreeter
                                            keeps com.org.jpms.service.internal unexported
jpms-app      module com.org.jpms.app      requires com.org.jpms.api          uses com.org.jpms.api.Greeter
```

`jpms-app` never depends on `jpms-service` at compile time. It finds
implementations through `ServiceLoader.load(Greeter.class)`, and Java's
module resolver pulls `jpms-service` into the module graph at run time
because it provides a service the app `uses`.

`jpms-service.internal` holds a public class (`GreetingFormatter`) that
is never `exports`ed — it's reachable inside the module but invisible
(compile *and* reflection) to `jpms-app` or anything else. That's the
strong-encapsulation half of JPMS; services are the loose-coupling half.

## <span style="color:hsl(2,80%,58%)">Build</span>

Root `pom.xml` has an unrelated broken module reference (`collections`,
pre-existing, unrelated to this change) that breaks a full reactor build.
Build this subtree directly instead:

```bash
cd jpms && mvn -N install          # install the aggregator pom
cd api && mvn install && cd ..
cd service && mvn install && cd ..
cd app && mvn install && cd ..
```

## <span style="color:hsl(139,80%,58%)">Run</span>

```bash
java --module-path api/target/jpms-api-1.0-SNAPSHOT.jar:service/target/jpms-service-1.0-SNAPSHOT.jar:app/target/jpms-app-1.0-SNAPSHOT.jar \
  -m com.org.jpms.app/com.org.jpms.app.Main YourName
```

Expected output:

```
[English] Hello, YourName!
[Spanish] Hola, YourName!
```

## <span style="color:hsl(277,80%,58%)">Inspect the module graph</span>

```bash
java --module-path api/target/*.jar:service/target/*.jar:app/target/*.jar \
  --describe-module com.org.jpms.service
```

Shows `contains com.org.jpms.service.internal` (not `exports`) — proof
the package is compiled in but sealed off from other modules.
