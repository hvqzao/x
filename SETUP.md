# Setup notes

### Kali Sana mirror

```
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

```
sudo aptitude install build-essential libpcap-dev rsh-client hostapd
sudo aptitude install dmz-cursor-theme mc vim-nox alacarte
```

### Hardware specific, T520

```
# T520 remap Prev/Next to PgUp/PgDn
cat >>~/.bashrc <<EOF

xmodmap -e 'keycode 167=Next'
xmodmap -e 'keycode 166=Prior'
EOF
```

### vim-nox

```
sudo cat >>/etc/vim/vimrc.local <<EOF
syntax on
EOF
```

### disable avahi

```
#/etc/rc.local
#chmod -x /usr/sbin/avahi-daemon
```

### iceweasel customization

```
# Customize iceweasel buttons, hide toolbar
```

### gnome-shell

```
# add "Hide App Icon" gnome extension

# (deprecated) Gnome Tweak Tool, Dash to Dock, Position and Size, Intelligent
# [ ] Push to show
```

### Google Chrome

```
wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
sudo dpkg -i google-chrome-stable_current_amd64.deb
sudo aptitude install libappindicator1
```

### Mozilla Firefox

```
# firefox 64bit en
# https://download.mozilla.org/?product=firefox-41.0.2-SSL&os=linux64&lang=en-US
# ~/x/b/firefox
```

### alacarte

```
# alacarte setup
# Firefox: /home/nme/x/b/firefox/firefox -P firefox -no-remote
# Iceweasel: /usr/bin/iceweasel -P iceweasel -no-remote
```

### Sun Java (JDK)

```
# download jdk to x/b
# ln -s jdk1.8.0_65/ jdk
# ln -s jdk1.8.0_65/ jre
```

### BurpSuite Pro


### Metasploit

```
sudo service postgresql start
sudo msfdb init
```

### Cairo Dock

```
sudo aptitude install cairo-dock
```

### msfconsole log

```
# (deprecated) 
# sudo echo "spool /root/msf_console.log" >/root/.msf4/msfconsole.rc
# Logs will be stored at /root/msf_console.log
```

### the-backdoor-factory

```
cd ~/x/p
git submodule add https://github.com/secretsquirrel/the-backdoor-factory
cd the-backdoor-factory
sudo ./install.sh
```

### httpscreenshot

```
cd ~/x/p
git submodule add https://github.com/breenmachine/httpscreenshot.git
pip install selenium
cd httpscreenshot/
chmod +x install-dependencies.sh && ./install-dependencies.sh
```

### smbexec

```
cd ~/x/p
git submodule add https://github.com/pentestgeek/smbexec.git
cd smbexec
./install.sh
```

### masscan

```
sudo aptitude install masscan
```

### praedasploit - printer exploits

```
cd ~/x/p
git submodule add https://github.com/MooseDojo/praedasploit
```

### sqlmap

```
cd ~/x/p
git submodule add https://github.com/sqlmapproject/sqlmap
```

### responder

```
cd ~/x/p
git submodule add https://github.com/SpiderLabs/Responder.git responder
```

### nosqlmap

```
cd ~/x/p
git submodule add https://github.com/tcstool/NoSQLMap.git nosqlmap
```

### seclists

```
cd ~/x/p
git submodule add https://github.com/danielmiessler/SecLists.git seclists
```

### net-creds pcap parser

```
cd ~/x/p
git submodule add https://github.com/DanMcInerney/net-creds.git
```

### wifite

```
cd ~/x/p
git submodule add https://github.com/derv82/wifite.git
```

### xxei

```
cd ~/x/p
git submodule add https://github.com/enjoiz/XXEinjector xxei
```

### upnp

```
sudo aptitude install upnp-inspector miniupnpc
```

### oracle sqldeveloper

```
# http://www.oracle.com/technetwork/developer-tools/sql-developer/overview/index-097090.html
# ~/x/b/sqldeveloper
```

### ve

```
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

```
cd ~/x/p
git submodule add https://github.com/portcullislabs/rdp-sec-check
sudo cpan
> install Encoding::BER
```

### Accesspoint - rtl8188eu

```
cd ~/x/p
git submodule add https://github.com/lwfinger/rtl8188eu
```

### windows-exploit-suggester

```
cd ~/x/p
git submodule add https://github.com/GDSSecurity/Windows-Exploit-Suggester.git windows-exploit-suggester
aptitude install python-xlrd
cd windows-exploit-suggester
./windows-exploit-suggester.py --update
```

### owtf

```
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

```
cd ~/x/p
git submodule add https://github.com/IOActive/jdwp-shellifier
```

### gwt-toolset

```
cd ~/x/p
#get clone https://github.com/GDSSecurity/GWT-Penetration-Testing-Toolset.git gwt-toolset
git submodule add https://github.com/GDSSecurity/GWT-Penetration-Testing-Toolset.git gwt-toolset
```

### vpnbook

```
cd ~/x/p
mkdir vpnbook
git submodule add https://github.com/Top-Hat-Sec/thsosrtl vpnbook/thsosrtl
cd vpnbook
ln -s thsosrtl/VeePeeNee/VeePeeNee.sh 
```

### empire

```
cd ~/x/p
git submodule add https://github.com/PowerShellEmpire/Empire empire
```


