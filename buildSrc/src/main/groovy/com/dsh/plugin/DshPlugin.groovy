package com.dsh.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class DshPlugin implements Plugin<Project> {
  @Override
  void apply(Project target) {
    def extension = target.extensions.create('dshPlugin', DshExtension)
    target.afterEvaluate {
      println "Hi ${extension.name}!"
    }
    def transform = new DshTransform()
    def baseExtension = target.extensions.getByType(BaseExtension)
    baseExtension.registerTransform(transform)
  }
}