## What was done

I attempted to implement all the requested features. There are a few defects to them.

I focused mainly on creating flexible and developer-friendly project structure. As such,
you can find these "modules" (I use `glow` in terminal to read this table):

subproject|what has|why
-|-|-
apilocal|Cache implementation for backend responses|OkHttp obeys server `no-cache` directive and does not provide cached response without server validation first. Room forces writing DTO <-> Entity converters, which is too much for just caching literal JSONs. I could not understand what [Store](https://github.com/MobileNativeFoundation/Store) lib wants from me with its "fetchers" and just created a quick'n'dirty file-based cache solution in 5 minutes.
apiremote|Retrofit stuff|
app|The app and the screens|I kept this one as is mostly, did not feel the need to move things out of there.
buildtools|Handy "convention" plugins for Gradle|These save lots of headaches by configuring subprojects in the same manner. An obvious improvement is to apply these plugins automatically, i.e. by having a naming convention for subprojects that hints their layer/feature belonging.
common|Things **all** other projects can use|Just provides some nice things like logging or BuildConfig for everyone to have access to. There is a cool `mapAsync()` extension too!
commontest|Stuff purely for testing|Has reusable coroutine rule which provides access to test dispatcher.
cryptowrapper|Pure-Kotlin definition for the CryptoLib|Domain layer cannot work with an `aar`-library, so I wrapped it into a more generic form.
data|DTOs|
design|Proto-design system :)|
domain|Domain models|I kept them encrypted since they are produced by the repo, which should not worry about decryption.
gradle|Version catalog|
repository|Weirdly, matches what its name says|Has some internet error handling because domain layer is not aware of the local cache.
repositoryboundary|Hosts repo interface|The plan was to make both repo and usecase depend on one small project to untie their compilations. Hilt did not find this cool though.
state|Domain-level stateful objects and their accessors|Some events like noticing Internet connectivity issues are important for the whole app (there is an indicator of offline work for instance, or the repo looks up cache based on it). These classes act as runtime storage to keep other classes stateless.
uimodels|UI models|These have decrypted fields, plus some things exclusive for UI. They are not super detailed though.
usecase|Stateless domain classes|Just data pipelines that consult with stateful domain objects and repo.

## TODO

There is a file with some of the tasks I chased.

## Used libs

lib|why
-|-
ktlint|Keeps the code minimally standardized
coreLibraryDesugaring|Allows to use `java.time`
DataStore|When the time came to track user interaction with "Download Image" button I had an opportunity to include Room again, but thankfully DataStore worked fine and it even provides a Map type. Its docs are incomplete sadly.

I did not do Compose since I concentrated more on refactoring and supporting the existing codebase,
which I find to be a more important skill. Surely, the migration is necessary, but I did not feel
like it is the most important task at this stage.

The same applies to navigation.

There are a few unit tests just to show that it is easy to write them (due to loose coupling of
components), and they also test some data cases.