package TudorCristea;

import TudorCristea.controllers.MainController;
import TudorCristea.models.CPU.CPUTesting;
import TudorCristea.models.GPU.GPUTesting;
import TudorCristea.models.Memory.MemoryTesting;
import TudorCristea.views.MainView;

public class App
{
    public static void main(String[] args)
    {
        MainController mainController = new MainController(new MainView());
    }
}