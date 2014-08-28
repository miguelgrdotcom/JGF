#!/bin/sh

export SECTION2="Crypt LUFact Matmult Series SOR"
export SECTION3="MolDyn Montecarlo RayTracer"
export SECTIONALL="Crypt Linpack Matmult Moldyn Montecarlo Raytracer SOR Series"
export JAVA_ARG="-Xmx50g -Xms50g -Xmn40g -XX:SurvivorRatio=10 -Dlogback.configurationFile=logback.xml" 
export ORIG_JAR=JGFTest/dist/JGFTest-noaspect.jar
export HU_JAR=JGFTest/dist/JGFTest.jar
export TEST_JAR=JGFTest/dist/JGFTest-tests.jar
export MPJ_HOME=libs/mpj-v0_43
export NUM_THREADS=16
export LOG_DIR=log
export TEST_DIR=test

# sequential

for i in ${SECTION2}; do for j in `seq 1 10`; do java ${JAVA_ARG}  -cp ${ORIG_JAR} jgf.sequential.JGF${i}BenchSizeC | tee ${LOG_DIR}/seq/orig-${i} 2>&1; done; done
for i in ${SECTION3}; do for j in `seq 1 10`; do java ${JAVA_ARG}  -cp ${ORIG_JAR} jgf.sequential.JGF${i}BenchSizeB | tee ${LOG_DIR}/seq/orig-${i} 2>&1; done; done
for i in ${SECTION2}; do for j in `seq 1 10`; do java ${JAVA_ARG} -cp ${HU_JAR} jgf.sequential.JGF${i}BenchSizeC | tee ${LOG_DIR}/seq/hu-${i} 2>&1; done; done
for i in ${SECTION3}; do for j in `seq 1 10`; do java  ${JAVA_ARG} -cp ${HU_JAR} jgf.sequential.JGF${i}BenchSizeB | tee ${LOG_DIR}/seq/hu-${i} 2>&1; done; done

# parallel

for i in ${SECTION2}; do for j in `seq 1 10`; do java ${JAVA_ARG}  -cp ${ORIG_JAR} jgf.parallel.JGF${i}BenchSizeC ${NUM_THREADS} | tee ${LOG_DIR}/par/orig-${i} 2>&1; done; done
for i in ${SECTION3}; do for j in `seq 1 10`; do java ${JAVA_ARG}  -cp ${ORIG_JAR} jgf.parallel.JGF${i}BenchSizeB ${NUM_THREADS} | tee ${LOG_DIR}/par/orig-${i} 2>&1; done; done
for i in ${SECTION2}; do for j in `seq 1 10`; do java ${JAVA_ARG} -cp ${HU_JAR} jgf.parallel.JGF${i}BenchSizeC ${NUM_THREADS} | tee ${LOG_DIR}/par/hu-${i} 2>&1; done; done
for i in ${SECTION3}; do for j in `seq 1 10`; do java  ${JAVA_ARG} -cp ${HU_JAR} jgf.parallel.JGF${i}BenchSizeB ${NUM_THREADS} | tee ${LOG_DIR}/par/hu-${i} 2>&1; done; done 

# distributed
for i in ${SECTION2}; do for j in `seq 1 10`; do ${MPJ_HOME}/bin/mpjrun.sh -np ${NUM_THREADS} ${JAVA_ARG}  -cp ${ORIG_JAR} jgf.mpi.JGF${i}BenchSizeC | tee ${LOG_DIR}/mpi/orig-${i} 2>&1; done; done
for i in ${SECTION3}; do for j in `seq 1 10`; do ${MPJ_HOME}/bin/mpjrun.sh -np ${NUM_THREADS} ${JAVA_ARG}  -cp ${ORIG_JAR} jgf.mpi.JGF${i}BenchSizeB | tee ${LOG_DIR}/mpi/orig-${i} 2>&1; done; done
for i in ${SECTION2}; do for j in `seq 1 10`; do ${MPJ_HOME}/bin/mpjrun.sh -np ${NUM_THREADS} ${JAVA_ARG} -cp ${HU_JAR} jgf.mpi.JGF${i}BenchSizeC | tee ${LOG_DIR}/mpi/hu-${i} 2>&1; done; done
for i in ${SECTION3}; do for j in `seq 1 10`; do ${MPJ_HOME}/bin/mpjrun.sh -np ${NUM_THREADS}  ${JAVA_ARG} -cp ${HU_JAR} jgf.mpi.JGF${i}BenchSizeB | tee ${LOG_DIR}/mpi/hu-${i} 2>&1; done; done 


# test for parallel
for i in ${SECTIONAL}; do for j in `seq 1 10`; do java ${JAVA_ARG} -cp ${HU_JAR}:${TEST_JAR} org.junit.runner.JUnitCore jgftest.${i} | tee ${TEST_DIR}/par/${i}.log 2>&1; done; done

# test for distributed
for i in ${SECTIONAL}; do for j in `seq 1 10`; do ${MPJ_HOME}/bin/mpjrun.sh -np ${NUM_THREADS} ${JAVA_ARG} -cp ${HU_JAR}:${TEST_JAR} jgftest.${i} | tee ${TEST_DIR}/mpi/${i}.log 2>&1; done; done
