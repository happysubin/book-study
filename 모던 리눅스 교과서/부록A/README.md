# 부록 A

## 시스템 정보 수집

리눅스 버전, 커널, 기타 관련 정보에 대해 알아보려면 다음 명령 중 하나를 사용해보자.

```shell
cat /etc/*-release
cat /proc/version
uname -a
```

기본 하드웨어 장비에 대해 알아보려면 다음 명령을 사용한다.

```shell
cat /proc/cpuinfo
cat /proc/meminfo
cat /proc/diskstats
```

BIOS 같은 시스템의 하드웨어에 대해 간단히 알아보려면 다음 명령을 사용하자.

```shell
sudo dmidecode -t bios
```

전체 주 메모리와 스왑 사용량을 쿼리하려면 다음을 사용

```shell
free -ht
```

프로세스가 가질 수 있는 파일 디스크립터 수를 쿼리하려면 다음을 사용하자.

```shell
ulimit -n
```

## 사용자와 프로세스 작업

who나 w를 사용하면 로그인한 사용자를 나열할 수 있다.

특정 사용자에 대해 프로세스별로 시스템 메트릭을 표시하려면 다음 명령을 사용하자.

```shell
top -U username
```

세부 정보와 함께 모든 프로세스를 트리 형식으로 나열하려면 다음 명령을 쓴다.

```shell
ps faux
```

특정 프로세스를 찾으려면 다음과 같이 한다.

```shell
ps -e | grep java
```

* ps는 process status의 약어. 
* -e는 시스템의 모든 프로세스에 대한 정보를 출력
* |는 한 명령어의 출력을 다른 명령어의 입력으로 전달하는 데 사용
* grep는 텍스트 검색 유틸리티로, 주어진 패턴과 일치하는 행을 출력


프로세스를 종료하려면 다음 명령을 사용

```shell
kill PID
```

## 파일 정보 수집

파일 세부 정보를 쿼리하려면 다음 명령을 사용

```shell
stat somfile
```

셸이 명령을 해석하는 방법과 실행 파일이 있는 위치 등에 관한 명령에 대해 알아보려면 다음과 같이 해본다.

```shell
type somecommand
type cat
which somebinaray
which cat
```

## 파일과 디렉토리 작업

텍스트 파일의 내용을 표시하려면 다음 명령을 사용하자.

```shell
cat afile
```

디렉토리의 내용을 나열하기 위해선 ls를 사용하면 되며, 해당 출력 값을 추가 명령에 사용할 수도 있다.
예를 들어 디렉터리의 파일 수를 계산하려면 다음과 같이 하면 된다.

```shell
ls -ls /etc | wc -l
```

파일과 파일 콘텐츠를 찾으려면 다음과 같이 한다.

```shell
find /etc -name "*.conf"
find . -type -f -exec grep -H FINDME {} \;
```

파일 간의 차이점을 보여주려면 다음 명령 사용.

```shell
diff -u somefile anotherfile
```

글자를 바꾸려면 다음과 같이 tr을 사용하자.

```shell
echo 'Com_Acme_Library' | tr '_A-Z' '.a-z'
```

문자열의 일부를 교체하는 또 다른 방법은 sed를 사용하는 것이다.

```shell
cat 'foo bar baz' | sed -e 's/foo/quux/'
```

테스트를 위해 특정 크기의 파일을 생성하려면 다음과 같이 dd 명령을 사용할 수 있다.

```shell
dd if=/dev/zero of=output.dat bs=102 count=1000
```

## 재지정과 파이프 작업

```shell
command 1> file
command 2> file
command &> file
command >file 2>&1
command > /dev/null
command < file
```

1. command의 stdout을 파일로 재지정
2. command의 stderr을 파일로 재지정
3. command의 stout와 stderr을 모두 파일로 재지정
4. command의 stdout과 stderr을 파일로 재지정하는 또 다른 방법
5. command 출력을 /dev/null로 재지정하여 폐기
6. stdin을 재지정

한 프로세스의 stdout을 다른 프로세스의 stdin에 연결하려면 파이프를 사용하자

```shell
cmd1 | cmd2 | cmd3
```

파이프의 각 명령의 종료 코드를 보여주려면 다음 명령을 사용

```shell
echo ${PIPESTATUS[@]}
```

## 시간과 날짜 작업

로컬 시간, UTC 시간, 동기화 상태와 같은 시간 관련 정보를 쿼리하려면 다음을 사용

```shell
timedatectl status
```

날짜 작업을 한다면 일반적으로 현재 시간에 대한 날짜나 타임스태프를 가져오는 작업을 하거나 기존 타임스탬프의 형식을 다른 것으로 변환하는 경우가 대부분이다.

YYYY-MM-DD 형식 으로 날짜를 가져오려면 다음 명령을 사용한다.

```shell
date +"%Y-%m-%d"
```

유닉스 에포크 타임스탬프를 생성하려면 다음 명령을 사용

```shell
date +%s
```

ISO 8601 형식의 UTC 타임스탬프를 생성하려면 다음 명령을 사용할 수 있다.

```shell
date -u +"%YY-%m-%dT%H:%M:%SZ"
```

동일한 ISO 8601 타임스탬프 형식이지만 로컬 시간의 경우에는 다음과 같이 하면 된다.

````shell
date +%FT%TZ
````

## 깃 작업

로컬 변경 사항을 컬러로 볼 수 있고, 추가되거나 삭제된 행을 나란히 표시 가능

```shell
git diff --color-moved
```

현재 커밋 ID 찾기

```shell
git rev-parse HEAD
```

커밋 로그의 요약을 보고 싶다면 아래 명령어

```shell
git log ..HEAD --oneline
```

## 시스템 성능

다음 명령을 사용해 메모리 로드를 가짜로 만들어보고 CPU 주기 도 일부로 소비 가능

```shell
yes | tr \\n x | head -c 450m | grep z
```

디렉터리의 자세한 디스크 사용량은 아래를 통해 확인

```shell
du -h /home
```

사용 가능한 디스크 공간 나열

```shell
df -h
```

다음을 사용해 디스크의 로드를 테스트하고 I/O 처리량 측정

```shell
dd if=/dev/zero of=/home/some/file bs=1G count=1 oflag=direct
```