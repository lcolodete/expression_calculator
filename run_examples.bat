set JAVA_HOME="C:\Program Files\Java\jdk1.8.0_112"

set EXPRESSION1="add(1, 2)"

set EXPRESSION2="sub(111, -30000)"

set EXPRESSION3=div(5,2)

set EXPRESSION4=add(1,mul(2,3))

set EXPRESSION5="mul(add(2, 2), div(9, 3))"

set EXPRESSION6="let(a, 5, add(a, a))"

set EXPRESSION7="let(a, 5, let(b, mul(a, 10), add(b, a)))"

set EXPRESSION8="let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))"



%JAVA_HOME%\bin\java -cp target\classes calculator.Calculator %EXPRESSION1%
%JAVA_HOME%\bin\java -cp target\classes calculator.Calculator %EXPRESSION2%
%JAVA_HOME%\bin\java -cp target\classes calculator.Calculator %EXPRESSION3%
%JAVA_HOME%\bin\java -cp target\classes calculator.Calculator %EXPRESSION4%
%JAVA_HOME%\bin\java -cp target\classes calculator.Calculator %EXPRESSION5%
%JAVA_HOME%\bin\java -cp target\classes calculator.Calculator %EXPRESSION6%
%JAVA_HOME%\bin\java -cp target\classes calculator.Calculator %EXPRESSION7%
%JAVA_HOME%\bin\java -cp target\classes calculator.Calculator %EXPRESSION8%

pause
