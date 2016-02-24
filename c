#!/bin/bash
# ~/x/c - control script
if [ "$#" -eq 0 ]
then
    echo "Usage: `basename $0` [pull] [update] [updated [[submodule] [file]]]" >&2
    exit 1
fi
cd ~/x
#for p in "$@"
while [ $# -ne 0 ]
do
    p=$1
    shift
    case "$p" in
        "pull")
            git pull origin master
            git submodule update --init --recursive
            git submodule foreach git checkout
            git checkout
            #git checkout -f origin/master
            ;;
        "update")
            find {b,p} -maxdepth 4 -name ".git" | sed 's/.git$//' | while read i
            do
                echo -e "\e[94m$i\e[39m"
                cd $i
                git pull origin master
            	git pull
            	git status
                cd - >/dev/null
            done
            ;;
        "updated")
            r=""
            if [ $# -ne 0 ]
            then
                r=$1
                shift
            fi
            f=""
            if [ $# -ne 0 ]
            then
                f=$1
                shift
            fi
            find {b,p} -maxdepth 4 -name ".git" | sed 's/.git$//' | while read i
            do
                old=`git diff $i | egrep "^-Subproject" | awk '{print $3}'`
                if [ "$old" != "" ]
                then
                    if [ "$r" != "" ]
                    then
                        if [ "$r" == "$i" -o "$r/" == "$i" ]
                        then
                            cd $i
                            if [ "$f" != "" ]
                            then
                                git diff $old -- $f
                            else
                                git diff $old --stat
                            fi
                            cd - >/dev/null
                        fi
                    else
                        echo -e "\e[94m$i\e[39m"
                    fi
                fi
            done
    esac
done
