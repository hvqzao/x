# Setup notes

### Kali Sana mirror

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

### aptitude

```sh
sudo aptitude install build-essential libpcap-dev rsh-client hostapd ethstats
sudo aptitude install dmz-cursor-theme mc vim-nox alacarte flashplugin-nonfree
```

### Hardware specific, T520

```sh
# T520 remap Prev/Next to PgUp/PgDn
cat >>~/.bashrc <<EOF

xmodmap -e 'keycode 167=Next'
xmodmap -e 'keycode 166=Prior'
EOF
```

### vim-nox

```sh
sudo cat >>/etc/vim/vimrc.local <<EOF
syntax on
EOF
```

### disable avahi

```sh
#/etc/rc.local
#chmod -x /usr/sbin/avahi-daemon
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
# Firefox: /home/nme/x/b/firefox/firefox -P firefox -no-remote
# Iceweasel: /usr/bin/iceweasel -P iceweasel -no-remote
```

### Sun Java (JDK)

```sh
# download jdk to x/b
# ln -s jdk1.8.0_65/ jdk
# ln -s jdk1.8.0_65/ jre
```

### BurpSuite Pro


### Metasploit

```sh
sudo service postgresql start
sudo msfdb init
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
cd ~/x/p
git submodule add https://github.com/secretsquirrel/the-backdoor-factory
cd the-backdoor-factory
sudo ./install.sh
```

### httpscreenshot

```sh
cd ~/x/p
git submodule add https://github.com/breenmachine/httpscreenshot.git
pip install selenium
cd httpscreenshot/
chmod +x install-dependencies.sh && ./install-dependencies.sh
```

### smbexec

```sh
cd ~/x/p
git submodule add https://github.com/pentestgeek/smbexec.git
cd smbexec
./install.sh
```

### masscan

```sh
sudo aptitude install masscan
```

### praedasploit - printer exploits

```sh
cd ~/x/p
git submodule add https://github.com/MooseDojo/praedasploit
```

### sqlmap

```sh
cd ~/x/p
git submodule add https://github.com/sqlmapproject/sqlmap
```

### responder

```sh
cd ~/x/p
git submodule add https://github.com/SpiderLabs/Responder.git responder
```

### nosqlmap

```sh
cd ~/x/p
git submodule add https://github.com/tcstool/NoSQLMap.git nosqlmap
```

### seclists

```sh
cd ~/x/p
git submodule add https://github.com/danielmiessler/SecLists.git seclists
```

### net-creds pcap parser

```sh
cd ~/x/p
git submodule add https://github.com/DanMcInerney/net-creds.git
```

### wifite

```sh
cd ~/x/p
git submodule add https://github.com/derv82/wifite.git
```

### xxei

```sh
cd ~/x/p
git submodule add https://github.com/enjoiz/XXEinjector xxei
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
cd ~/x/b
https://github.com/hvqzao/ve
cd ve
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
cd ~/x/p
git submodule add https://github.com/portcullislabs/rdp-sec-check
sudo cpan
> install Encoding::BER
```

### Accesspoint - rtl8188eu

```sh
cd ~/x/p
git submodule add https://github.com/lwfinger/rtl8188eu
```

### windows-exploit-suggester

```sh
cd ~/x/p
git submodule add https://github.com/GDSSecurity/Windows-Exploit-Suggester.git windows-exploit-suggester
aptitude install python-xlrd
cd windows-exploit-suggester
./windows-exploit-suggester.py --update
```

### owtf

```sh
sudo pip install --upgrade cffi
sudo pip install --upgrade cryptography
cd ~/x/p
wget https://raw.githubusercontent.com/owtf/bootstrap-script/master/bootstrap.sh
chmod +x bootstrap.sh
sudo ./bootstrap.sh
# 2,1
mv bootstrap.sh owtf/p_bootstrap.sh
```

### jdwp-shellifier

```sh
cd ~/x/p
git submodule add https://github.com/IOActive/jdwp-shellifier
```

### gwt-toolset

```sh
cd ~/x/p
#get clone https://github.com/GDSSecurity/GWT-Penetration-Testing-Toolset.git gwt-toolset
git submodule add https://github.com/GDSSecurity/GWT-Penetration-Testing-Toolset.git gwt-toolset
```

### vpnbook

```sh
cd ~/x/p
mkdir vpnbook
git submodule add https://github.com/Top-Hat-Sec/thsosrtl vpnbook/thsosrtl
cd vpnbook
ln -s thsosrtl/VeePeeNee/VeePeeNee.sh 
```

### empire

```sh
cd ~/x/p
git submodule add https://github.com/PowerShellEmpire/Empire empire
```

### ipport

```sh
cd ~/x/p
git submodule add https://github.com/hvqzao/ipport
```

### udp-proto-scanner

```sh
cd ~/x/p
git submodule add https://github.com/portcullislabs/udp-proto-scanner
```

### FIX https support for wfuzz

```sh
sudo sed -i 's/SSL_VERIFYHOST,1/SSL_VERIFYHOST,0/g' /usr/share/wfuzz/reqresp.py
```

### nishang

```
cd ~/x/p
git submodule add https://github.com/samratashok/nishang
```

### ysoserial

```
cd ~/x/p
mkdir ysoserial
cd ysoserial
wget "https://github.com/frohoff/ysoserial/releases/download/v0.0.2/ysoserial-0.0.2-all.jar"
git submodule add https://github.com/frohoff/ysoserial src
```

### oracle client basic+sqlplus (x64)

# http://www.oracle.com/technetwork/topics/linuxx86-64soft-092277.html
# installation instructions are there, target: /opt/oracle/instantclient_12_1
