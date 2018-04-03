set JAVA_HOME="C:\Program Files\Java\jdk1.8.0_112"

set EXPRESSION=let(a,5,let(b,mul(a,10),add(b,a)))

%JAVA_HOME%\bin\java -cp target\classes calculator.Calculator %EXPRESSION%

pause
