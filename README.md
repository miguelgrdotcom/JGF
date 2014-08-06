JGF
===

# 通常テスト実行

* 逐次と並列は、NetBeansからテスト実行

# MPIテスト実行

* NetBeansでテスト実行した後に生成されるクラスを使う

> $MPJ_HOME/bin/mpjrun.sh -np 4 -cp ./build/test/classes:/Users/yoshiki/NetBeansProjects/JGF/JGFTest/dist/JGFTest.jar -Dlogback.configurationFile=$WORK/logback.xml jgftest.JGFTest

* その後、NetBeansでビルドした際に、テストクラスもビルドし、別にアーカイブされるよう変更したため、下記コマンドでも実行可能(logback省略)
> $MPJ_HOME/bin/mpjrun.sh -np 4 -cp JGFTest/dist/JGFTest.jar:JGFTest/dist/JGFTest-tests.jar jgftest.JGFTest
