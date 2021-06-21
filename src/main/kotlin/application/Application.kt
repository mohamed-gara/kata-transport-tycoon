package application

class Application {
  fun transport(containers: String): Int =
    if (containers.isEmpty()) 0 else 5
}
