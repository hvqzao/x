#!/bin/bash
# ~/x/c - control script
if [ "$#" -eq 0 ]
then
    echo "Usage: `basename $0` [pull] [update] [updated [[submodule] [file]]] [pip-upgrade] [remove-submodule [path]]" >&2
    exit 1
fi
cd ~/x

remove-submodule() {

    # https://gist.github.com/sharplet/6289697
    # Adam Sharp
    # Aug 21, 2013
    
    submodule_name=$(echo "$1" | sed 's/\/$//'); shift
    echo "$submodule_name"

    if git submodule status "$submodule_name" >/dev/null 2>&1; then
        git submodule deinit -f "$submodule_name"
        git rm -f "$submodule_name"
    
        #git config -f .gitmodules --remove-section "submodule.$submodule_name"
        if [ -z "$(cat .gitmodules)" ]; then
            git rm -f .gitmodules
        else
            git add .gitmodules
        fi
        rm -rf ".git/modules/$submodule_name"
    else
        echo "Submodule '$submodule_name' not found" >&2
        return 1
    fi
}

pull() {
    git stash
    #git pull origin master
    git pull --rebase --stat
    #git checkout
    git stash pop
    git submodule update --init --recursive
    git submodule foreach git checkout 
    #git checkout -f origin/master
}

update() {
    echo -e "\e[94mDownloading releases...\e[39m"
    cd ~/x/e/releases
    ./get-newest-releases
    cd - >/dev/null
    cd ~/x
    git submodule update --init --recursive
    git submodule foreach git checkout 
    cd - >/dev/null
    find {b,p} -maxdepth 4 -name ".git" | sed 's/.git$//' | while read i
    do
	echo -e "\e[94m$i\e[39m"
	cd $i
	#git checkout
	#git pull origin master
	git pull --rebase --stat || { git checkout master && git pull --rebase --stat origin master ; }
	#git status
	cd - >/dev/null
    done
}

#for p in "$@"
while [ $# -ne 0 ]
do
    p=$1
    shift
    case "$p" in
        "pull")
            pull
            ;;
        "update")
            update
            ;;
        "updated")
            # repo
            r=""
            if [ $# -ne 0 ]
            then
                r=$1
                shift
            fi
            # file
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
            ;;
        "remove-submodule")
            remove-submodule "$1"
            shift
            ;;
        "pip-upgrade")
            echo "# Usage: ~/x/c pip-upgrade | bash"
            echo 'pip freeze --local | awk -F= "{print $1}" | xargs -n1 -- pip install --upgrade'
            ;;
    esac
done
