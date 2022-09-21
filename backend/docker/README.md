
## docker 실행 가이드라인
- 편의성을 위해 쉘 스크립트 작성.
- 실행 방법
  - IntelliJ 좌측 Project탭에서 실행하고자 하는 sh 파일을 우클릭하여 run한다.
  - 또는, 각 파일에 실행권한을 부여하고 터미널 상에서 실행한다.
    - 예시) `chmod +x run.sh`

### docker 실행
명령어 : `./script/run.sh`

### docker 종료
명령어 : `./script/down.sh`

### docker shell 접속
명령어 : `./script/connect_shell.sh` -> (root 자동 접속)

### docker mysql 접속
명령어 : `./script/connect_mysql.sh` -> (root 자동 접속)
