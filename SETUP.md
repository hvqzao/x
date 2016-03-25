# Setup

### Installation

```sh
cd ~
git clone https://github.com/hvqzao/x
~/x/c pull
```

See below for additonal configuration steps

### (skip) The following will be already installed. No action required.

aptitude:

```sh
sudo aptitude install thc-ssl-dos slowhttptest
```

submodules:

```sh
#cd ~/x/b
#git submodule add https://github.com/hvqzao/ve
#git submodule add https://github.com/hvqzao/endec
#cd ~/x/p
#git submodule add https://github.com/hatRiot/clusterd
#git submodule add https://github.com/psypanda/hashID hashid
#git submodule add https://github.com/PaulSec/HQLmap hqlmap
#git submodule add https://github.com/enjoiz/BSQLinjector bsql-injector
#git submodule add https://github.com/secretsquirrel/the-backdoor-factory
#git submodule add https://github.com/breenmachine/httpscreenshot.git
#git submodule add https://github.com/pentestgeek/smbexec.git
#git submodule add https://github.com/MooseDojo/praedasploit
#git submodule add https://github.com/sqlmapproject/sqlmap
#git submodule add https://github.com/SpiderLabs/Responder.git responder
#git submodule add https://github.com/tcstool/NoSQLMap.git nosqlmap
#git submodule add https://github.com/danielmiessler/SecLists.git seclists
#git submodule add https://github.com/DanMcInerney/net-creds.git
#git submodule add https://github.com/derv82/wifite.git
#git submodule add https://github.com/enjoiz/XXEinjector xxei
#git submodule add https://github.com/sophron/wifiphisher
#git submodule add https://github.com/leebaird/discover.git
#git submodule add https://github.com/portcullislabs/rdp-sec-check
#git submodule add https://github.com/lwfinger/rtl8188eu
#git submodule add https://github.com/GDSSecurity/Windows-Exploit-Suggester.git windows-exploit-suggester
#git submodule add https://github.com/IOActive/jdwp-shellifier
#git submodule add https://github.com/GDSSecurity/GWT-Penetration-Testing-Toolset.git gwt-toolset
#git submodule add https://github.com/PowerShellEmpire/Empire empire
#git submodule add https://github.com/hvqzao/ipport
#git submodule add https://github.com/portcullislabs/udp-proto-scanner
#git submodule add https://github.com/samratashok/nishang
#git submodule add https://github.com/rotlogix/liffy
#git submodule add https://github.com/aboul3la/Sublist3r sublistbrute
#git submodule add git clone https://github.com/frohoff/ciphr.git
#git submodule add https://github.com/minimaxir/big-list-of-naughty-strings blns
#git submodule add https://github.com/ewilded/get_docroots get-docroots
```

vpnbook:

```sh
#cd ~/x/p
#mkdir vpnbook
#git submodule add https://github.com/Top-Hat-Sec/thsosrtl vpnbook/thsosrtl
#cd vpnbook
#ln -s thsosrtl/VeePeeNee/VeePeeNee.sh 
```

ysoserial:

```sh
#cd ~/x/p
#mkdir ysoserial
#cd ysoserial
#wget "https://github.com/frohoff/ysoserial/releases/download/v0.0.3/ysoserial-0.0.3-all.jar"
#git submodule add https://github.com/frohoff/ysoserial src
```

burp plugins:

```sh
#cd ~/x/p/burp/unofficial
#wget https://github.com/DirectDefense/SuperSerial/releases/download/0.3/superserial-passive-0.3.jar
#git submodule add https://github.com/DirectDefense/SuperSerial super-serial
#wget https://github.com/hvqzao/burp-flow/releases/download/v1.02/flow.jar
#git submodule add https://github.com/hvqzao/burp-flow flow
#wget https://github.com/hvqzao/burp-wildcard/releases/download/v1.02/wildcard.jar
#git submodule add https://github.com/hvqzao/burp-wildcard wildcard
```

.war webshell:

```sh
#cd $HOME/x/p/fuzzdb
#mkdir webshell
#cp cmd.jsp webshell/
#cd webshell
#jar -cvf ../webshell.war *
#cd ..
#rm -rf webshell
#mkdir $HOME/x/e/webshells
#mv webshell.war !$
# Usage after deploying: /webshell/cmd.jsp?cmd=id
```

inyourface (java faces attacks):

```sh
#cd ~/x/p
#wget http://www.synacktiv.com/ressources/inyourface-0.2.tar.gz
#tar zxvf inyourface-0.2.tar.gz
#cd inyourface
#sudo aptitude install ant
#ant
#cd ..
#rm inyourface-0.2.tar.gz
```

### (optional) hardware specific, Lenovo T520

```sh
# Lenovo T520 remap Prev/Next to PgUp/PgDn
cat >>~/.bashrc <<EOF

xmodmap -e 'keycode 167=Next'
xmodmap -e 'keycode 166=Prior'
EOF
```

### (old) Kali Sana mirror

/etc/apt/sources.list:

```sh
# http://http.kali.org/README.mirrorlist
# http://security.kali.org/README.mirrorlist

#deb http://http.kali.org/kali sana main non-free contrib
#deb-src http://http.kali.org/kali sana main non-free contrib
#deb http://archive-3.kali.org/kali sana main non-free contrib
#deb-src http://archive-3.kali.org/kali sana main non-free contrib
deb http://kali.mirror.garr.it/mirrors/kali sana main non-free contrib
deb-src http://kali.mirror.garr.it/mirrors/kali sana main non-free contrib

#deb http://security.kali.org/kali-security/ sana/updates main contrib non-free
#deb-src http://security.kali.org/kali-security/ sana/updates main contrib non-free
#deb http://archive-5.kali.org/kali-security/ sana/updates main contrib non-free
#deb-src http://archive-5.kali.org/kali-security/ sana/updates main contrib non-free
deb http://kali.mirror.garr.it/mirrors/kali-security/ sana/updates main contrib non-free
deb-src http://kali.mirror.garr.it/mirrors/kali-security/ sana/updates main contrib non-free
```

### (new) Kali Rolling mirror

/etc/apt/sources.list:

```sh
deb http://archive-3.kali.org/kali kali-rolling main non-free contrib
deb-src http://archive-3.kali.org/kali kali-rolling main non-free contrib
```

```sh
aptitude update
aptitude dist-upgrade
aptitude -f install
aptitude clean
find /etc | grep \.dpkg-
```

### aptitude

```sh
sudo apt-get install aptitude
sudo aptitude install build-essential libpcap-dev rsh-client hostapd ethstats irssi \
dmz-cursor-theme mc vim-nox alacarte flashplugin-nonfree ipcalc htop chromium lftp
```

### vmware-tools

```sh
aptitude install open-vm-tools-desktop fuse zerofree
```

### pip

```sh
pip install --upgrade pip
pip install --upgrade selenium
```

### disable avahi

```sh
#/etc/rc.local
#chmod -rwx /usr/sbin/avahi-daemon
```

### openvas (fix greenbone-security-assistant daemon listening on port 80):

```sh
mkdir -p /etc/systemd/system/greenbone-security-assistant.service.d 
cat >/etc/systemd/system/greenbone-security-assistant.service.d/local.conf <<EOF
[Service]
ExecStart=
ExecStart=/usr/sbin/gsad --foreground --listen=127.0.0.1 --port=9392 --mlisten=127.0.0.1 --mport=9390 --no-redirect
EOF
systemctl daemon-reload
systemctl restart greenbone-security-assistant
```

### iceweasel customization

```sh
# Customize iceweasel buttons, hide toolbar
```

### gnome-shell

```sh
# add "Hide App Icon" gnome extension

# (deprecated) Gnome Tweak Tool, Dash to Dock, Position and Size, Intelligent
# [ ] Push to show
```

### Google Chrome

```sh
wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo dpkg -i google-chrome-stable_current_amd64.deb
sudo aptitude install libappindicator1
```

### Mozilla Firefox

```sh
# firefox 64bit en
# https://download.mozilla.org/?product=firefox-41.0.2-SSL&os=linux64&lang=en-US
# ~/x/b/firefox
```

### alacarte

```sh
# alacarte setup
# Firefox: $HOME/x/b/firefox/firefox -P firefox -no-remote
# Iceweasel: /usr/bin/iceweasel -P iceweasel -no-remote
```

### Sun Java (JDK)

```sh
# download jdk to x/b
# ln -s jdk1.8.0_66/ jdk
# ln -s jdk1.8.0_66/jre
# download & install Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files
```

```sh
sudo update-alternatives --install "/usr/bin/java" java $HOME/x/b/jdk/bin/java 1
sudo update-alternatives --set java $HOME/x/b/jdk/bin/java
sudo update-alternatives --install "/usr/bin/rmiregistry" rmiregistry $HOME/x/b/jdk/bin/rmiregistry 1
sudo update-alternatives --set rmiregistry $HOME/x/b/jdk/bin/rmiregistry
sudo update-alternatives --install "/usr/bin/rmid" rmid $HOME/x/b/jdk/bin/rmid 1
sudo update-alternatives --set rmid $HOME/x/b/jdk/bin/rmid
sudo update-alternatives --install "/usr/bin/policytool" policytool $HOME/x/b/jdk/bin/policytool 1
sudo update-alternatives --set policytool $HOME/x/b/jdk/bin/policytool
sudo update-alternatives --install "/usr/bin/keytool" keytool $HOME/x/b/jdk/bin/keytool 1
sudo update-alternatives --set keytool $HOME/x/b/jdk/bin/keytool
sudo update-alternatives --install "/usr/bin/servertool" servertool $HOME/x/b/jdk/bin/servertool 1
sudo update-alternatives --set servertool $HOME/x/b/jdk/bin/servertool
```

### vim: vimrc.local

### BurpSuite Pro

### Metasploit

```sh
sudo service postgresql start
sudo msfdb init
sudo update-rc.d postgresql enable
```

### Cairo Dock

```sh
sudo aptitude install cairo-dock
```

### msfconsole log

```sh
# (deprecated) 
# sudo echo "spool /root/msf_console.log" >/root/.msf4/msfconsole.rc
# Logs will be stored at /root/msf_console.log
```

### the-backdoor-factory

```sh
cd ~/x/p/the-backdoor-factory
sudo ./install.sh
```

### httpscreenshot

```sh
cd ~/x/p/httpscreenshot/
chmod +x install-dependencies.sh && sudo ./install-dependencies.sh
```

### smbexec

```sh
cd ~/x/p/smbexec
sudo ./install.sh
#1,/opt
```

### masscan

```sh
sudo aptitude install masscan
```

### upnp

```sh
sudo aptitude install upnp-inspector miniupnpc
```

### oracle sqldeveloper

```sh
# http://www.oracle.com/technetwork/developer-tools/sql-developer/overview/index-097090.html
# ~/x/b/sqldeveloper
```

### ve

```sh
cd ~/x/b/ve
sudo ./ve -r
./ve -p
./ve -P 2.7.10
./ve -P 3.5.0
./ve -n 3.5.0
./ve -N 3.5.0 5.0.0
cat >.ve <<EOF 
PY=py-3.5.0
NODE=node-5.0.0
EOF
. ./ve py-2.7.10
deact
. ./ve
deact
```

### rdp-sec-check

```sh
# requirements
sudo cpan
> install Encoding::BER
```

### windows-exploit-suggester

```sh
# requirements + data source
aptitude install python-xlrd
cd ~/x/p/windows-exploit-suggester
./windows-exploit-suggester.py --update
```

### owtf

```sh
sudo pip install --upgrade cffi
sudo pip install --upgrade cryptography
cd ~/x/p
git rm -r p/owtf --cached
wget https://raw.githubusercontent.com/owtf/bootstrap-script/master/bootstrap.sh
chmod +x bootstrap.sh
rm -rf owtf
sudo ./bootstrap.sh
# 2,1
sudo mv bootstrap.sh owtf/p_bootstrap.sh
```

### FIX https support for wfuzz (obsolete)

```sh
#sudo sed -i 's/SSL_VERIFYHOST,1/SSL_VERIFYHOST,0/g' /usr/share/wfuzz/reqresp.py
```

### oracle client basic+sqlplus (x64)

```sh
# http://www.oracle.com/technetwork/topics/linuxx86-64soft-092277.html
# installation instructions are there, target: /opt/oracle/instantclient_12_1
```

### testssl

```sh
cd ~/x/p
mkdir testssl
cd testssl
wget https://testssl.sh/CHANGELOG.txt
wget https://testssl.sh/LICENSE.txt
wget https://testssl.sh/OPENSSL-LICENSE.txt
wget https://testssl.sh/bash-heartbleed.changelog.txt
wget https://testssl.sh/bash-heartbleed.sh
wget https://testssl.sh/ccs-injection.sh
wget https://testssl.sh/mapping-rfc.txt
wget https://testssl.sh/openssl-1.0.2e-chacha.pm.ipv6.Linux.tar.gz
wget https://testssl.sh/openssl-1.0.2e-chacha.pm.ipv6.Linux.tar.gz.asc
wget https://testssl.sh/openssl-1.0.2e-chacha.pm.tar.gz
wget https://testssl.sh/openssl-1.0.2e-chacha.pm.tar.gz.asc
wget https://testssl.sh/openssl-rfc.mappping.html
wget https://testssl.sh/testssl.sh
wget https://testssl.sh/testssl.sh.asc
chmod +x *.sh
tar zxvf openssl-1.0.2e-chacha.pm.tar.gz
# usage: testssl --openssl=bin <...>
```

### discover

```sh
cd ~/x/p/discover
sudo ./update.sh
sudo ln -s $HOME/x/p/discover /opt
```

### heartbleed

```sh
cd ~/x/p
mkdir heartbleed
cd heartbleed
wget https://raw.githubusercontent.com/HackerFantastic/Public/master/exploits/heartbleed.c
#gcc heartbleed.c -o heartbleed -Wl,-Bstatic -lssl -Wl,-Bdynamic -lssl3 -lcrypto
gcc -lssl -lssl3 -lcrypto heartbleed.c -o heartbleed
chmod +x heartbleed
```

### soap-ui

```sh
# http://www.soapui.org/downloads/soapui/open-source.html 
cd ~/x/b/soap-ui
wget http://cdn01.downloads.smartbear.com/soapui/5.2.1/SoapUI-x64-5.2.1.sh
chmod +x SoapUI-x64-5.2.1.sh 
./!$
```

### ciphr

```sh
gem install bundler
cd ~/x/p/ciphr
bundle install
sudo bundle exec rake install
```

### odat

```sh
# follow https://github.com/quentinhardy/odat#installation-optional
# to meet requirements: sqlplus, pip install cx_Oracle
```
