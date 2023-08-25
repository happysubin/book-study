CREATE INDEX i_사용여부 ON 급여 (사용여부);

CREATE INDEX ui_부서명 ON 부서 (부서명);

CREATE INDEX i_부서번호 ON 부서관리자 (부서번호);

CREATE INDEX i_부서번호 ON 부서사원 (부서번호);

CREATE INDEX i_입사일자 ON 사원 (입사일자);

CREATE INDEX i_성별_성 ON 사원 (성별, 성);

CREATE INDEX i_출입문 ON 사원출입기록 (출입문);

CREATE INDEX i_지역 ON 사원출입기록 (지역);

CREATE INDEX i_시간 ON 사원출입기록 (입출입시간);

