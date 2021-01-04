# IMGDroid
MGDroid is an Eclipse project.Then please follow the following three steps to make the code run:

1.Import the project first:
After openning Eclipse, please click: ①File ②Import ③General ④Existing Projects into Workspace;
Then click "Next",At the line "Select root directory: Browse", you should click Browse and select this directory, make sure that all projects in the directory are selected, then click Finish.

2.Modify params:
Please open Java file "tool.Result.testall.java" in the IMGDroid to modify the variables "androidPlatform" and "FileDirectory" to your own. "androidPlatform" refers to the Android sdk platforms folder, and "FileDirectory" refers to the folder where apks are located.

3.Run: 
Run testall.java,the results of IMGDroid will be generated. The variable "ResultExcelLocation" in "tool.Result.EntryForAll.java" in the IMGDroid project determines the folder where the experiment results are outputted. You may change it. By default, the output is generated in the root directory of the IMGDroid project directory, e.g., if the IMGDroid project directory is F:\Documents\IMGDroid, then the output path is F:\Documents\IMGDroid\Results.xls.

