# x

Pentest environment scaffolding.

## Installation

```sh
cd ~
git clone https://github.com/hvqzao/x
~/x/c pull
```

For further deployment instructions, refer to [SETUP#post-installation](SETUP.md#post-installation). Tested in Kali 2 (Rolling).

To pull updates from https://github.com/hvqzao/x repository:

```sh
~/x/c pull
```

To fetch newest upstream versions of all projects in repository:

```sh
~/x/c update
```

To see newest updates (general, package, file diff):

```sh
~/x/c updated
~/x/c updated p/discover
~/x/c updated p/discover notes/burp.txt
```

### Structure

```
 ~/x/
   ├─── b/  - binary installations
   ├─   d/  - development*
   ├─── e/  - extras
   ├─   h/  - host files*
   ├─── p/  - pentest tools
   ├─   r/  - research*
   ├─── t/  - tests
   ├─   s/  - sandbox*
   ├─   u/  - untrusted files*
   └─   w/  - IDE workspace*

 *optional
```

### Tools

```
b/
b/eclipse                           - Eclipse IDE
b/endec                             - Encrypt / Decrypt using aes-256-cbc
b/firefox                           - Firefox (aside to Iceweasel, with -no-remote switch)
b/jdk                               - Oracle Java Development Kit
b/jre                               - Oracle Java Runtime
b/soap-ui                           - SOAP-based API testing
b/sqldeveloper                      - Oracle SQL Developer
b/ve                                - Python 2,3, NodeJS virtualenvs rapid setup and use

e/
e/msf                               - msfconsole handler templates
e/releases/foolav                   - AV bypass
e/releases/LaZagne                  - passwords gathering (outlook, browsers, winscp, ftp...)
e/rtl8188eu                         - Hostapd configs (rtl8188eu and original)
e/webshells/webshell.war            - .war webshell

p/
p/blns                              - The Big List of Naughty Strings
p/bsql-injector                     - Blind-SQL server responses retrieval / data extraction
p/burp                              - Burp pro, Burp free, addons and unofficial plugins
p/ciphr                             - Data transposition, encryption, decryption, hashing etc
p/discover                          - Pentest supporting tools and notes
p/empire                            - Powershell attack framework
p/fuzzdb                            - Fuzzing database (still in active development)
p/get-docroots                      - LFI discovery helper script
p/gwt-toolset                       - GWT application testing support
p/heartbleed                        - Heartbleed vulnerability exploitation
p/hqlmap                            - Sqlmap for HQL (Hibernate Query Language)
p/inyourface                        - Java Faces attack tool
p/ipport                            - Rapid ICMP/TCP/UDP scanning scripts
p/jdwp-shellifier                   - JDWP (Java Debug Wire Protocol) to RCE
p/liffy                             - LFI (Local File Inclusion) exploitation
p/net-creds                         - Sniff searches / creds from interface or .pcap file
p/nishang                           - Powershell-aided penetration testing
p/nosqlmap                          - Sqlmap for NoSQL
p/odat                              - Oracle Database Attacking Tool
p/owtf                              - Offensive Web Testing Framework + nice Kali Linux fine-tune
p/praedasploit                      - Printer attacks
p/rdp-sec-check                     - RDP security checks
p/responder                         - LLMNR/NBT-NS/MDNS poisoner, rogue HTTP/SMB/MSSQL/FTP/LDAP
p/rtl8188eu                         - rtl8188 specific hostapd + setup scripts
p/seclists                          - Fuzzing database (fuzzdb fork)
p/smbexec                           - psexec style attacks
p/sqlmap                            - Sqlmap
p/sublistbrute                      - Subdomains enumeration and brute-forcing
p/testssl                           - Testing for SSL vulnerabilities
p/the-backdoor-factory              - TBF - backdooring MITM
p/udp-proto-scanner                 - UDP enumeration and probing
p/vpnbook                           - Free VPN
p/wifiphisher                       - Wifi attack framework
p/windows-exploit-suggester         - systeminfo comparison against exploit database
p/xxei                              - XXE Injector
p/ysoserial                         - Java Deserialization Attacks
```

## License

[MIT License](https://github.com/twbs/bootstrap/blob/master/LICENSE)
