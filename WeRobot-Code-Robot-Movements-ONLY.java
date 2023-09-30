package org.firstinspires.ftc.teamcode;
//package fr.gzod01.werobot.competition_singapour_2023;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="WeRobot: FGC2023", group="WeRobot")
public class WeRobot_FGC2023_GZod01 extends LinearOpMode {
  private DcMotor flm;
  private DcMotor frm;
  private DcMotor brm;
  private DcMotor blm;
  private DcMotor moissonmotor;
  private DcMotor launchermotor;
  private DcMotor mountmotor;

  @Override
  public void runOpMode() {
        float x;
        double y;
        float g2x;
        boolean ismoissonactive=false;
        boolean ga;
        boolean gy;
        boolean preva =false;
        flm = hardwareMap.get(DcMotor.class, "flm");
        frm = hardwareMap.get(DcMotor.class, "frm");
        blm = hardwareMap.get(DcMotor.class, "blm");
        brm = hardwareMap.get(DcMotor.class, "brm");
        moissonmotor = hardwareMap.get(DcMotor.class, "msn");
        launchermotor = hardwareMap.get(DcMotor.class, "lnc");
        mountmotor = hardwareMap.get(DcMotor.class, "mnt");
        WRRobot wr = new WRRobot(flm,frm,blm,brm);
        waitForStart();

        while (opModeIsActive()) {
            x = gamepad1.left_stick_x;
            y = -gamepad1.left_stick_y;
            ga = gamepad1.a;
            gy = gamepad1.y;
            if(ga & !preva){
                    ismoissonactive=!ismoissonactive;
                    preva=true;
            }
            if(!ga & preva){
                    preva =false;
            }
            if(ismoissonactive){
                    moissonmotor.setPower(1);
            }else{
                    moissonmotor.setPower(0);
            }
            if(gy){
                    launchermotor.setPower(1);
            }else{
                    launchermotor.setPower(0);
            }
            if (gamepad1.dpad_down){
                    mountmotor.setPower(-1);
            }else if (gamepad1.dpad_up){
                    mountmotor.setPower(1);
            }else{
                    mountmotor.setPower(0);
            }


            g2x = gamepad1.right_stick_x;
            wr.moves(x,y );
            wr.Rotation(g2x );
        }
    }
}
class WRRobot{
        private DcMotor flm;
        private DcMotor frm;
        private DcMotor blm;
        private DcMotor brm;
        public WRRobot(DcMotor cflm,DcMotor cfrm,DcMotor cblm,DcMotor cbrm){
                flm = cflm;
                frm = cfrm;
                blm = cblm;
                brm = cbrm;
        }
        public  void moves(float x, double y){
                double approx = 0.15;
                boolean xnozero = (x<-approx || x>approx);
                boolean ynozero = (y<-approx || y>approx);

                if(!(Math.abs(x)>approx || Math.abs(y)>approx)){
                        flm.setPower(0);
                        frm.setPower(0);
                        blm.setPower(0);
                        brm.setPower(0);
                        return;
                }

                //DO TRANSLATIONS
                if(Math.abs(x)>approx){
                        if(Math.abs(y)>approx){
                                diagonal(x,y);
                        }else{
                                horizontal(x);
                        }
                }else{
                        vertical(y);
                }

        }
        public void diagonal(float x, double y){
                double pow = Math.abs(x+y)/2;
                if(x>0){
                        if(y>0){
                                // North East
                                movement(new double[]{pow,0,0,-pow});

                        }else{
                                //South East
                                movement(new double[]{0,pow,-pow,0});
                        }
                }else{
                        if(y>0){
                                //North West
                                movement(new double[]{0,-pow,pow,0});
                        }
                        else{
                                //South West
                                movement(new double[]{-pow,0,0,pow});
                        }
                }
        }
        public  void horizontal(float x){
                double pow = Math.abs(x);
                if(x>0){
                        movement(new double[]{pow,pow,-pow,-pow});
                }else{
                        movement(new double[]{-pow,-pow,pow,pow});
                }



        }
        public  void vertical(double y){
                double pow = Math.abs(y);
                if(y>0){
                        movement(new double[]{pow,-pow,pow,-pow});
                }else{
                        movement(new double[]{-pow,pow,-pow,pow});
                }

        }
        public  void movement(double[] list){
                flm.setPower(list[0]);
                frm.setPower(list[1]);
                blm.setPower(list[2]);
                brm.setPower(list[3]);
        }

        /*
         * @param g2x is the x coordinates of the right joystick
         */
        public  void Rotation(float g2x){
                double approx = 0.15;
                boolean xnozero = (g2x<-approx || g2x>approx);
                double pow = Math.abs(g2x);
                if (!xnozero) return;
                if (g2x > 0) {
                        movement(new double[]{pow,pow,pow,pow});
                } else {
                        movement(new double[]{-pow,-pow,-pow,pow});
                }
        }


}
