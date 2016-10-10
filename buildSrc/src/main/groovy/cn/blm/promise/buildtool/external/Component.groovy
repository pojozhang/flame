package cn.blm.promise.buildtool.external

import cn.blm.promise.buildtool.Environment
import org.gradle.api.Project

/**
 * @author jiaan.zhang@oracle.com
 * @date 06/10/2016 1:50 AM
 */
abstract class Component {

    enum Status {
        RUNNING,
        STOPPED
    }

    static final Environment environment = new Environment()
    String name = null
    String host = null
    String homeDir = null
    String port = null
    String version = null
    Project project = null
    Map<String, Object> properties = new HashMap<String, Object>();

    Component(String name, Project project) {
        this(name, project, null)
    }

    Component(String name, Project project, Settings settings) {
        this.name = name
        this.project = project
        if (settings) {
            this.homeDir = settings.homeDir
            this.host = settings.host
            this.port = settings.port
            this.version = settings.version
        }
    }

    abstract boolean isAlive()

    Status getStatus() {
        return isAlive() ? Status.RUNNING : Status.STOPPED
    }

    abstract void start()

    abstract void stop()

    static class Settings {
        String homeDir = null
        String host = null
        String port = null
        String version = null
    }
}
