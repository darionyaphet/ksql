/*
 * Copyright 2018 Confluent Inc.
 *
 * Licensed under the Confluent Community License; you may not use this file
 * except in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.confluent.io/confluent-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.confluent.ksql.parser.tree;

import static com.google.common.base.MoreObjects.toStringHelper;
import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

public class FrameBound
    extends Node {

  public enum Type {
    UNBOUNDED_PRECEDING,
    PRECEDING,
    CURRENT_ROW,
    FOLLOWING,
    UNBOUNDED_FOLLOWING
  }

  private final Type type;
  private final Optional<Expression> value;

  public FrameBound(final Type type) {
    this(Optional.empty(), type);
  }

  public FrameBound(final NodeLocation location, final Type type) {
    this(Optional.of(location), type);
  }

  public FrameBound(final Type type, final Expression value) {
    this(Optional.empty(), type, value);
  }

  private FrameBound(final Optional<NodeLocation> location, final Type type) {
    this(location, type, null);
  }

  public FrameBound(final NodeLocation location, final Type type, final Expression value) {
    this(Optional.of(location), type, value);
  }

  private FrameBound(
      final Optional<NodeLocation> location,
      final Type type,
      final Expression value) {
    super(location);
    this.type = requireNonNull(type, "type is null");
    this.value = Optional.ofNullable(value);
  }

  public Type getType() {
    return type;
  }

  public Optional<Expression> getValue() {
    return value;
  }

  @Override
  public <R, C> R accept(final AstVisitor<R, C> visitor, final C context) {
    return visitor.visitFrameBound(this, context);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if ((obj == null) || (getClass() != obj.getClass())) {
      return false;
    }
    final FrameBound o = (FrameBound) obj;
    return Objects.equals(type, o.type)
           && Objects.equals(value, o.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, value);
  }

  @Override
  public String toString() {
    return toStringHelper(this)
        .add("type", type)
        .add("value", value)
        .toString();
  }
}
