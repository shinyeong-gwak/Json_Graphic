package frame;

public enum Command {
    CMD(String.format("""
            scp -P 7020 %s mlms@210.125.93.235:~
            ssh -p 7020 mlms@210.125.93.235;
            cd ./ns-allinone-3.39/ns-3.39/;
            ./ns3 run "wifi-mlms --config=./examples/mlms/gui/test-scenario.json" >> ./examples/mlms/gui/output;
            
            
              
            
            
            
            
            
            """,MainFrame.jsonPath.getText()));
    private final String cmd;
    Command(String s) {
        this.cmd = s;
    }

    public String get() {
        return cmd;
    }
}
