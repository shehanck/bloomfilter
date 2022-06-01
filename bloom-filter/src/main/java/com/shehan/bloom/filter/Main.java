package com.shehan.bloom.filter;

/**
 *
 * @author Shehan Charuka
 */
public class Main {

    /**
     * Accepts bitmap size, hash count and dictionary file path as command line arguments and
     * start SpellCheckerApplication with those parameters.
     * @param args Accepts bitmap size, hash count and dictionary file path as command line arguments
     *
     */
    public static void main(String[] args) {
        String[] argv = new String[3];
        if(args.length>0){
            if(args.length%2==0){
                for(int i=0;i<args.length-1;i=i+2){
                    if(args[i].trim().equals("-bitmapsize")){
                        argv[0]=args[i+1].trim();
                    } else if(args[i].trim().equals("-hashcount")){
                        argv[1]=args[i+1].trim();
                    } else if(args[i].trim().equals("-file")){
                        argv[2]=args[i+1].trim();
                    } else {
                        System.out.println("Unsupported option");
                        System.exit(1);
                    }
                }
            } else {
                System.out.println("Invalid no: of arguments");
                System.exit(1);
            }
        }
        SpellCheckerApplication spellCheckerApplication = new SpellCheckerApplication();
        spellCheckerApplication.start(argv);
    }
    
}
