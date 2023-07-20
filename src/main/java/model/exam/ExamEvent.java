package model.exam;

public class ExamEvent {
    private final ExamIF source;

    public ExamEvent (ExamIF src) {
        source = src;
    }//costruttore
    public ExamIF getSource() {
        return source;
    }//getSource

}//ExamEvent
