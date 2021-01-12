# IMGDroid
IMGDroid is an Eclipse project.Then please follow the following three steps to make the code run:

1.Import the project first:
After openning Eclipse, please click: ①File ②Import ③General ④Existing Projects into Workspace;
Then click "Next",At the line "Select root directory: Browse", you should click “Browse” and select the directory where the IMGDroid project is located , then click "Finish".

2.Modify parameters:
Please open Java file "tool.Result.testall.java" in the IMGDroid to modify the variables "androidPlatform" and "FileDirectory" to your own. "androidPlatform" refers to the Android sdk platforms folder, and "FileDirectory" refers to the folder where apks(apps) to be analyzed are located.

3.Run: 
Run testall.java, and after a while (the time length depends on how many apps are analyzed) the results of IMGDroid will be generated. The variable "ResultExcelLocation" in "tool.Result.EntryForAll.java" in the IMGDroid project determines the folder where the experiment results are generated. You may change it. By default, the output is generated in the root directory of the IMGDroid project directory, e.g., if the IMGDroid Project directory is F:\Documents\IMGDroid, then the output path is F:\Documents\IMGDroid\Results.xls. Note that if the console reports an error during the running process, you can still get the corresponding result.

