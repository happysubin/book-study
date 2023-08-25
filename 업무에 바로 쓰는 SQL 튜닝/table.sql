CREATE TABLE 사원 (
    사원번호 INT NOT NULL AUTO_INCREMENT,
    생년월일 DATE,
    이름 VARCHAR(20),
    성 VARCHAR(50),
    성별 VARCHAR(50),
    입사일자 DATE,
    PRIMARY KEY(사원번호)
);

CREATE TABLE 부서 (
    부서번호 INT NOT NULL AUTO_INCREMENT,
    비고 VARCHAR(50),
    PRIMARY KEY(부서번호)
);

CREATE TABLE 부서사원 (
    사원번호 INT NOT NULL,
    부서번호 INT NOT NULL,
    시작일자 DATE,
    종료일자 DATE,
    PRIMARY KEY(사원번호, 부서번호)
);

CREATE TABLE 부서관리자 (
    사원번호 INT NOT NULL,
    부서번호 INT NOT NULL,
    시작일자 DATE,
    종료일자 DATE,
    PRIMARY KEY(사원번호, 부서번호)
);

CREATE TABLE 직급 (
    사원번호 INT NOT NULL,
    직급명 VARCHAR (50),
    시작일자 DATE,
    종료일자 DATE,
    PRIMARY KEY(사원번호, 직급명, 시작일자)
);

CREATE TABLE 급여 (
    사원번호 INT NOT NULL,
    연봉 INT NOT NULL,
    시작일자 DATE,
    종료일자 DATE,
    사용여부 VARCHAR (50),
    PRIMARY KEY(사원번호, 시작일자)
);

CREATE TABLE 사원출입기록 (
    순번 INT NOT NULL AUTO_INCREMENT,
    사원번호 INT NOT NULL,
    입출입시간 DATE,
    입출입구분 VARCHAR (50),
    출입문 VARCHAR (50),
    지역 VARCHAR (50),
    PRIMARY KEY(순번, 사원번호)
);