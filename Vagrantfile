Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/trusty64"

  config.vm.network "forwarded_port", guest: 8080, host: 8080
  config.vm.provider "virtualbox" do |vm|
     vm.memory = "1024"
  end

  # Dependencies
  config.vm.provision "shell", inline: <<-SHELL
    add-apt-repository ppa:openjdk-r/ppa -y
    apt-get update -y
    apt-get install -y --force-yes curl
    apt-get install -y --force-yes unzip
    apt-get install -y --force-yes openjdk-8-jdk
    apt-get install -y --force-yes maven
    apt-get install
  SHELL

  # Spring Boot
  config.vm.provision "shell", run: 'always', inline: <<-SHELL
    cd /vagrant/
    mvn spring-boot:run
  SHELL
end
