#!/bin/zsh

os=('Linux', 'macOS', 'Windows') #배열 정의
echo "${os[0]}" #첫 번째 요소에 접근함. Linux 출력
numberofos="${#os[@]}"
echo $numberofos