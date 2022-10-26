# Keygen

## ssh-keygen -t rsa
```
kang@ubuntu:~$ ssh-keygen -t rsa
  Generating public/private rsa key pair.
  Enter file in which to save the key (/home/kang/.ssh/id_rsa): 
  Enter passphrase (empty for no passphrase): 
  Enter same passphrase again: 
  Your identification has been saved in /home/kang/.ssh/id_rsa.
  Your public key has been saved in /home/kang/.ssh/id_rsa.pub.
  The key fingerprint is:
  SHA256:P5Jc2XzFMyauRmGPtFV6lC6GmQHBll51pjbDVBC49Hk kang@ubuntu
  The key's randomart image is:
  +---[RSA 2048]----+
  |        .o+ o=+*.|
  |         + =o Bo |
  |        o o+BXo=o|
  |         .oOO=*Eo|
  |        S o++o+  |
  |       . +. ..   |
  |        + oo     |
  |         ...     |
  |                 |
  +----[SHA256]-----+
kang@ubuntu:~$
```
## tree .ssh
```
kang@ubuntu:~$ tree .ssh
  .ssh
  ├── id_rsa
  └── id_rsa.pub

  0 directories, 2 files
kang@ubuntu:~$ 
```
## print id_rsa(private key), id_rsa.pub(public key)
```
kang@ubuntu:~$ cat .ssh/id_rsa
-----BEGIN RSA PRIVATE KEY-----
MIIEowIBAAKCAQEAysbtdLaT8F8bMSVER9iRUp41w+jDllGO2SynBc+CJj4/XOBc
LRy1ssz0Lk3AWPZ07+tNDd2eaF2yVjhbu5Vg0oFIn0FPrP5+2c2UGAuVvqcx1pNB
...
3I7hPQKBgALmlN0xvsd1Ay4YzWlongWKvQDoRsOG/i4TvtIPvMz4Ya0pKQIVoXat
ZpwF5GAk+iDj1Y0MLYgWu+dMHGrlOx7rYJn2163kfFiMONpkkzycD/H/RPZzyNk5
JdZU0hSWuukvFflB9m6GWqVyJaMWhdVjzc6eoUd4Ekfx6jt2gJL4
-----END RSA PRIVATE KEY-----
kang@ubuntu:~$ cat .ssh/id_rsa.pub
ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDKxu10tpP ... fAiZg06E1Xz/DTJoPdMJbqmWfqj kang@ubuntu
kang@ubuntu:~$ 
```
