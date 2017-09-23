DECLARE
  AS_COMP_CD VARCHAR2(4);
  AS_EMP_ID VARCHAR2(20);
  v_Return VARCHAR2(200);
BEGIN
  AS_COMP_CD := NULL;
  AS_EMP_ID := NULL;

  v_Return := F_GET_EMP_NAME2(
    AS_COMP_CD => AS_COMP_CD,
    AS_EMP_ID => AS_EMP_ID
  );
  /* Legacy output: 
DBMS_OUTPUT.PUT_LINE('v_Return = ' || v_Return);
*/ 
  :v_Return := v_Return;
--rollback; 
END;
