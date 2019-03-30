package com.csming.percent.common.widget.statuslayout;

public class NotExistNumException extends Exception {
    public NotExistNumException(){
        super();
    }
    public NotExistNumException(String gride){
        super(gride);
    }

    @Override
    public void printStackTrace(){
        super.printStackTrace();
        System.out.print("You can't choice 1,2,3 as your status key");
}
}