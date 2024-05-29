package dev.heowc.heo.cli;

import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.standard.ShellComponent;

@ShellComponent
@Command
public class HeoCommand {

    private final HeoCliService service;

    public HeoCommand(HeoCliService service) {
        this.service = service;
    }

    @Command(command = "heo")
    public void doHeo(@Option(shortNames = 'd', required = true) String directory,
                      @Option(shortNames = 'p', required = true) String prefixPackage,
                      @Option(shortNames = 'o') String destination) {
        if (StringUtils.isBlank(destination)) {
            destination = String.format("result-%s.png", System.currentTimeMillis());
        }
        service.command(directory, prefixPackage, destination);
    }
}
