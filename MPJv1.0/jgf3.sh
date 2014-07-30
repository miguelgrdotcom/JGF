#!/bin/sh

JGF_PATH=/group1/gc83/c83001/JGF/MPJv1.0
SECTION=section3

. /group1/gc83/share/env.sh ompi
export CLASSPATH=${CLASSPATH}:${JGF_PATH}:${JGF_PATH}/${SECTION}
mpiexec java JGFAllSizeB
