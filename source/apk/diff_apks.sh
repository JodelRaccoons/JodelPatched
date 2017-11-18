#!/bin/bash
diffapk () {
    if type "colordiff" > /dev/null; then
        colordiff <(zipinfo -1 $1) <(zipinfo -1 $2)
    else
        diff <(zipinfo -1 $1) <(zipinfo -1 $2)
    fi
}
diffapk $1 $2

