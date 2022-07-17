package com.beyond.pagesORCmds;

import com.beyond.helpers.ActionsHelper;

public interface Data_cmd {

     int waitTime =60;
     String getLatestBuild ="/usr/local/bin/docker image pull";
     String createProjectTag ="Create Project";

     String projectName="Auto Had "+ ActionsHelper.generateRandomText();
     String makeFeature ="make-features";

    String  projectDirPath="${dirPath}/src/main/resources/DataProvider/Study3:/study";
    String dockerPath="/usr/local/bin/docker";
    String studyFolderName="Study3";

   String createStudy ="create-study-configuration";
   String model663= "model_663";
   String templateSimpleRF="simple_rf";
     public static void main(String[] args) {
          System.out.println(projectDirPath.replace("${dirPath}","src/hadeel"));
    }



}
