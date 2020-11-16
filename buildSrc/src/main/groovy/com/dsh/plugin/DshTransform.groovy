package com.dsh.plugin

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils;

class DshTransform extends Transform {
  @Override
  String getName() {
    return 'dshTransform'
  }

  @Override
  Set<QualifiedContent.ContentType> getInputTypes() {
    return TransformManager.CONTENT_CLASS
  }

  @Override
  Set<? super QualifiedContent.Scope> getScopes() {
    return TransformManager.SCOPE_FULL_PROJECT
  }

  @Override
  boolean isIncremental() {
    return false
  }

  @Override
  void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
    def inputs = transformInvocation.inputs
    def outputProvider = transformInvocation.outputProvider
    inputs.each {
      it.jarInputs.each {
        File dest = outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.JAR)
        println "Jar: ${it.file}, Dest: ${dest}"
        FileUtils.copyFile(it.file, dest)
      }
      it.directoryInputs.each {
        File dest = outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.DIRECTORY)
        println "Dir: ${it.file}, Dest: ${dest}"
        FileUtils.copyDirectory(it.file, dest)
      }
    }
  }
}
