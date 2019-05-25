# Setup

## Installation

```sh
cd ~
git clone https://github.com/hvqzao/x
~/x/c pull
```

See below for additonal configuration steps

### The following will be already installed. No action required.

# skip to [SETUP#post-installation](SETUP.md#post-installation) 

aptitude:

```sh
sudo aptitude install thc-ssl-dos slowhttptest
```

### busybox:

```sh
cd ~/x/p/busybox
./get
```

### vpnbook:

```sh
#cd ~/x/p
#mkdir vpnbook
#git submodule add https://github.com/Top-Hat-Sec/thsosrtl vpnbook/thsosrtl
#cd vpnbook
#ln -s thsosrtl/VeePeeNee/VeePeeNee.sh 
```

### ysoserial:

```sh
#cd ~/x/p
#mkdir ysoserial
#cd ysoserial
#wget "https://github.com/frohoff/ysoserial/releases/download/v0.0.3/ysoserial-0.0.3-all.jar"
#git submodule add https://github.com/frohoff/ysoserial src
```

### burp plugins:

```sh
#cd ~/x/p/burp/unofficial
#wget https://github.com/DirectDefense/SuperSerial/releases/download/0.3/superserial-passive-0.3.jar
#git submodule add https://github.com/DirectDefense/SuperSerial super-serial
#wget https://github.com/hvqzao/burp-flow/releases/download/v1.02/flow.jar
#git submodule add https://github.com/hvqzao/burp-flow flow
#wget https://github.com/hvqzao/burp-wildcard/releases/download/v1.02/wildcard.jar
#git submodule add https://github.com/hvqzao/burp-wildcard wildcard
```

### .war webshell:

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

### inyourface (java faces attacks):

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

## Post-installation

### Kali adjustments

```sh
sudo ln -s /sbin/ifconfig /bin/ifconfig
```

### aptitude install supplementary packages, and post configuration

```sh
sudo apt-get install aptitude
sudo aptitude install build-essential libpcap-dev rsh-client hostapd ethstats irssi \
dmz-cursor-theme mc vim-nox alacarte flashplugin-nonfree ipcalc htop chromium lftp \
gcc-mingw-w64-i686 gcc-mingw-w64-x86-64 clusterssh mitmf ethtool bettercap veil-evasion \
veil-catapult golang

veil-evasion
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

### rc.local

```sh
vim /etc/systemd/system/rc-local.service
```

```
[Unit]
Description=/etc/rc.local
ConditionPathExists=/etc/rc.local

[Service]
Type=forking
ExecStart=/etc/rc.local start
TimeoutSec=0
StandardOutput=tty
RemainAfterExit=yes
SysVStartPriority=99

[Install]
WantedBy=multi-user.target
```

```sh
vim /etc/rc.local
```

```
#!/bin/sh -e
#
# rc.local
#
# This script is executed at the end of each multiuser runlevel.
# Make sure that the script will "exit 0" on success or any other
# value on error.
#
# In order to enable or disable this script just change the execution
# bits.
#
# By default this script does nothing.

exit 0
```

```sh
chmod +x /etc/rc.local
systemctl enable rc-local
systemctl start rc-local.service
systemctl status rc-local.service
```

### disable avahi, bluetooth

```sh
#/etc/rc.local
chmod -rwx /usr/sbin/avahi-daemon
rfkill block bluetooth
which vmhgfs-fuse >/dev/null && { mkdir -p /mnt/hgfs ; vmhgfs-fuse -o allow_other /mnt/hgfs ; } || true
```

### openvas (fix greenbone-security-assistant daemon listening on port 80):

```sh
# openvas-setup
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

### mate-desktop-environment

```sh
# sudo aptitude install mate-desktop-environment
```

### Google Chrome

```sh
wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo dpkg -i google-chrome-stable_current_amd64.deb
sudo aptitude install libappindicator1 libcurl3
```

### Mozilla Firefox

```sh
# firefox 64bit en
# https://download.mozilla.org/?product=firefox-41.0.2-SSL&os=linux64&lang=en-US
# ~/x/b/firefox
```

### alacarte / mozo (MATE)

```sh
# alacarte setup
# Firefox: $HOME/x/b/firefox/firefox -P firefox -no-remote
# Iceweasel: /usr/bin/iceweasel -P iceweasel -no-remote
```

### Sun Java (JDK)

```sh
# download jdk to x/b
cd ~/x/b
# ln -s jdk1.8.0_66/ jdk
a=`ls -trd jdk*.* | tail -1` ; ln -s $a jdk
# ln -s jdk1.8.0_66/jre
a=`ls -trd jdk*.* | tail -1` ; ln -s $a/jre
# download & install Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files
```

manual update-alternatives:

```sh
sudo update-alternatives --install "/usr/bin/java" java $HOME/x/b/jdk/bin/java 1
sudo update-alternatives --set java $HOME/x/b/jdk/bin/java
```

automated:

```sh
for i in `update-alternatives --get-selections | grep -- "-openjdk-" | awk '{print $1}'` ; do [ -e "$HOME/x/b/jdk/bin/$i" ] &&
sudo update-alternatives --install "/usr/bin/$i" "$i" "$HOME/x/b/jdk/bin/$i" 1 && sudo update-alternatives --set "$i"
"$HOME/x/b/jdk/bin/$i" ; done
```

### vim: vimrc.local

### BurpSuite Pro

### odat (provides sqlplus)

```sh
# follow https://github.com/quentinhardy/odat#installation-optional
# to meet requirements: sqlplus, pip install cx_Oracle
```

### Metasploit

```sh
sudo service postgresql start
sudo msfdb init
sudo update-rc.d postgresql enable
```

### Metasploit oci8 support (requires sqlplus)

```sh
# https://github.com/rapid7/metasploit-framework/wiki/How-to-get-Oracle-Support-working-with-Kali-Linux
cd
wget https://github.com/kubo/ruby-oci8/archive/ruby-oci8-2.1.8.zip
unzip ruby-oci8-2.1.8.zip
cd ruby-oci8-2.1.8
make
make install
cd -
rm -rf ruby-oci8*
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

### sniper

```sh
cd ~/x/p/sniper
sudo ./install.sh
```

### john (jumbo)

```sh
cd ~/x/r
wget wget http://www.openwall.com/john/j/john-1.8.0-jumbo-1.tar.gz
#doc/INSTALL-UBUNTU
```

```diff
--- MD5_std.c	2016-07-02 14:28:06.855918106 +0200
+++ MD5_std.c.orig	2016-07-02 14:27:52.775918373 +0200
@@ -480,8 +480,7 @@
  * is large enough.
  */
 #ifdef __x86_64__
-//#define MAYBE_INLINE_BODY MAYBE_INLINE
-#define MAYBE_INLINE_BODY
+#define MAYBE_INLINE_BODY MAYBE_INLINE
 #else
 #define MAYBE_INLINE_BODY
 #endif
```

```sh
./configure
make
```

### wifi-pumpkin

```sh
cd ~/x/p/wifi-pumpkin
./installer.sh --install
# requires "export QT_X11_NO_MITSHM=1" in /etc/profile.d
```

### EyeWitness

```sh
cd ~/x/p/eyewitness/setup
./setup.sh
```

