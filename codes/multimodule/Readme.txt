javac --module-source-path src -m main.app,core.app  -d out
java --module-path out -m main.app/example.app.AppMain

jar --create --file ./pkg/core.app.jar -C out/core.app . 
jar --create --file ./pkg/app.main.jar --main-class=example.app.AppMain  -C out/main.app .
java --module-path pkg --module main.app/example.app.AppMain

jar --describe-module --file pkg/core.app.jar
jar --describe-module --file pkg/app.main.jar