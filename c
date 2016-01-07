#!/bin/bash
# ~/x/c - control script
if [ "$#" -eq 0 ]
then
    echo "Usage: `basename $0` [--update] [--update-modules]" >&2
    exit 1
fi
cd ~/x
for p in "$@"
do
    case "$p" in
        "--update")
            git pull
            git submodule update --init --recursive
            git submodule foreach git checkout
            ;;
        "--update-modules")
            find {b,p} -maxdepth 3 -name ".git" | sed 's/.git$//' | while read i
            do
                echo -e "\e[94m$i\e[39m"
                cd $i
                git pull
                cd - >/dev/null
            done
            ;;
    esac
done
