JGF
===

# 通常テスト実行

* 逐次と並列は、NetBeansからテスト実行

# MPIテスト実行

* NetBeansでテスト実行した後に生成されるクラスを使う

> $MPJ_HOME/bin/mpjrun.sh -np 4 -cp ./build/test/classes:/Users/yoshiki/NetBeansProjects/JGF/JGFTest/dist/JGFTest.jar -Dlogback.configurationFile=$WORK/logback.xml jgftest.JGFTest
