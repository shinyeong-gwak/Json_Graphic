package frame;

public enum Command {
    CMD(String.format("""
            sshpass -p '9207144mlms' 'scp -P 7020 %s mlms@210.125.93.235:~/ns-allinone-3.39/ns-3.39/examples/mlms/'
            mlms
            cd ./ns-allinone-3.39/ns-3.39/;
            ./ns3 run "wifi-mlms --config=./examples/mlms/gui/test-scenario-7.json" >> /dev/pts/2;
            
              
            
            
            
            
            
            """,MainFrame.jsonPath.getText()));
    private final String cmd;
    Command(String s) {
        this.cmd = s;
    }

    public String get() {
        return cmd;
    }
}
