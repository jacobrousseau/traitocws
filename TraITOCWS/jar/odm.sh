#!/bin/bash

#	Copyright 2012 VU Medical Center Amsterdam
#
#	Licensed under the Apache License, Version 2.0 (the "License");
#	you may not use this file except in compliance with the License.
#	You may obtain a copy of the License at
#
#	    http://www.apache.org/licenses/LICENSE-2.0
#
#	Unless required by applicable law or agreed to in writing, software
#	distributed under the License is distributed on an "AS IS" BASIS,
#	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#	See the License for the specific language governing permissions and
#	limitations under the License.



# Wrapper for ODM export and import
# Requires java 1.6 build 30

# 2012, VU Medical Center Amsterdam
# Author: Arjan van der Velde 

JARFILE="traitocws.jar"

if [ "X$OC_URL" != "X" ]; then
	OC_URL=-b"$OC_URL"
fi
if [ "X$OC_USER" != "X" ]; then
	OC_USER=-u"$OC_USER"
fi
if [ "X$OC_PASSWORD" != "X" ]; then
	OC_PASSWORD=-p"$OC_PASSWORD"
fi

#set -x

# determine action
if [ "X$1" = "X--extract" ]; then
	# extract
	shift
	java -cp "$JARFILE" nl.vumc.trait.oc.main.ExtractODM "$OC_URL" "$OC_USER" "$OC_PASSWORD" "$@"
elif [ "X$1" = "X--import" ]; then
	# import
	shift
	java -cp "$JARFILE" nl.vumc.trait.oc.main.ImportODM "$OC_URL" "$OC_USER" "$OC_PASSWORD" "$@"
elif [ "X$1" = "X--subjects" ]; then
	# subjects
	shift
	java -cp "$JARFILE" nl.vumc.trait.oc.main.ListSubjects "$OC_URL" "$OC_USER" "$OC_PASSWORD" "$@"
elif [ "X$1" = "X--clean" ]; then
	# clean
	shift
	java -cp "$JARFILE" nl.vumc.trait.oc.main.CleanODM "$OC_URL" "$OC_USER" "$OC_PASSWORD" "$@"
elif [ "X$1" = "X--studies" ]; then
	# studies
	shift
	java -cp "$JARFILE" nl.vumc.trait.oc.main.ListStudies "$OC_URL" "$OC_USER" "$OC_PASSWORD" "$@"
else
	echo "Usage: `basename $0` < --extract | --import | --subjects | --clean | --studies > [ -h | --help ] <command specific options>"
fi


