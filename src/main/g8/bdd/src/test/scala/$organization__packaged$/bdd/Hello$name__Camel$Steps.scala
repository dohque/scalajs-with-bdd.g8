package $organization$.bdd

import cucumber.api.{PendingException, Scenario}
import cucumber.api.scala.{EN, ScalaDsl}
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.{is, notNullValue}
import org.openqa.selenium.{By, WebDriver}
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}

import scala.jdk.CollectionConverters._

class Hello$name;format="Camel"$Steps extends ScalaDsl with EN {

  val webDriver: WebDriver = {
    val chromeOptions = new ChromeOptions()
    chromeOptions.setExperimentalOption("mobileEmulation",
      Map("deviceName" -> "iPhone 6/7/8").asJava)
    val chromeDriver = new ChromeDriver(chromeOptions)
    Runtime.getRuntime.addShutdownHook(new Thread(() => chromeDriver.quit()))
    chromeDriver
  }

  Given("""I have a web browser"""){ () =>
    assertThat(webDriver, notNullValue())
  }

  When("""I open the App"""){ () =>
    webDriver.get("http://localhost:8080/")
  }

  Then("""I see {string}"""){ name:String =>
    val header = webDriver.findElement(By.xpath(s"//*/h1[text()='\$name']"))
    assertThat(header.getText().trim, is("Hello $name;format="Camel"$!"))
  }
}
