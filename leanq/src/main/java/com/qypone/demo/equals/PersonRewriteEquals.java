package com.qypone.demo.equals;

public class PersonRewriteEquals {
  private String name;
  private int age;

  public PersonRewriteEquals(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  /**
   * 只比较name
   * @param o
   * @return
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonRewriteEquals that = (PersonRewriteEquals) o;
    return name.equals(that.name);
  }

}
