Build & debug instructions (Windows / cmd.exe)

What I tried
- I attempted to run the Gradle build from this environment to collect compiler errors, but the IDE's terminal couldn't create a classic terminal window (the environment blocked running gradlew here).
- I ran the project's internal error checker for Java source files; it reported no Java compile errors in the main sources.

Why I need your local build output
- The Gradle build runs annotation processors, resource linking, AAPT, and other steps that can produce errors that are not visible from simple Java compile checks. To locate and fix build failures I need the full Gradle assemble output.

How to run the build locally (Windows cmd.exe)
1. Open a cmd.exe prompt.
2. Change directory to the project root (replace path if your project is elsewhere):

   cd "C:\Development\Android_studio\Android App\Android App\Androble-master\Homework"

3. Run the Gradle wrapper with stacktrace and no-daemon to get full output:

   gradlew.bat assembleDebug --stacktrace --no-daemon > build-output.txt 2>&1

   This writes the full build output to build-output.txt. If the build fails, open that file and copy the failing stacktrace and error messages into your reply.

4. If you prefer streaming output to the console instead of a file, run:

   gradlew.bat assembleDebug --stacktrace --no-daemon

What I can do once you provide the build output
- I will parse the Gradle error output and fix build errors one-by-one in the repository.
- If you prefer, enable the Classic Terminal in your IDE (View -> Tool Windows -> Classic Terminal) and allow me to run the build here, or paste the build output file.

Quick checklist (what I already checked)
- [x] Ran static Java error check across main sources — no Java compile errors found.
- [x] Confirmed Gradle wrapper configuration (gradle-wrapper.properties uses Gradle 9.0) and top-level plugin (AGP 8.4.2) look consistent.

If you'd like me to proactively apply low-risk fixes before seeing the build error (for example, migrate deprecated Ad APIs or add a missing runtime permission guard), tell me which fixes you want me to try; otherwise please run the build locally and paste the failing output here and I will fix each error directly.
